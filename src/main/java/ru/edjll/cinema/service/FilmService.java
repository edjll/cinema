package ru.edjll.cinema.service;

import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.multipart.MultipartFile;
import ru.edjll.cinema.domain.*;
import ru.edjll.cinema.repository.FilmRepo;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class FilmService {

    @Autowired
    private FilmRepo filmRepo;

    @Autowired
    private FileService fileService;

    @Autowired
    private AccessService accessService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private SubscribeService subscribeService;

    @Autowired
    private RatingService ratingService;

    private static final int PAGE_SIZE = 11;

    public List<Film> getFilms() {
        return filmRepo.findAll(Sort.by("id").descending());
    }

    public Film getFilmById(Long id) {
        return filmRepo.findById(id).orElse(null);
    }

    public List<Film> getDesiredFilms(String searchParam, int page) {
        return getDesiredFilms(searchParam, page, PAGE_SIZE);
    }

    public List<Film> getDesiredFilms(String searchParam, int page, int size) {
        return filmRepo.findAllByTitleContainsOrDescriptionContainsIgnoreCase(searchParam, searchParam, PageRequest.of(page, size, Sort.by("id")));
    }

    public Collection<Object> convertFilm(Collection<Film> films) {
        return films.stream()
                .map(
                    film -> {
                        Map<String, Object> model = new HashMap<>();
                        model.put("id", film.getId());
                        model.put("title", film.getTitle());
                        model.put("description", film.getDescription());
                        model.put("category", film.getCategories().stream()
                                .map(category -> category.getName())
                                .collect(Collectors.toList()));
                        String subs = subscribeService.getActiveSubscribesByFilm(film.getId()).size() > 0 && film.getAccess().getType() == AccessType.BUY ? ", Подписка" : "";
                        model.put("access", film.getAccess().getType().getTitle() + subs);
                        model.put("date", film.getReleaseDate());
                        model.put("ageLimit", film.getAgeLimit());
                        Double rating = ratingService.getAverageRating(film);
                        model.put("rating", rating == null ? "нет голосов" : rating);
                        model.put("preview", film.getPreview());
                        return model;
                    }
                ).collect(Collectors.toList());
    }

    public Film save(
            Film film,
            AccessType accessType,
            Double price,
            MultipartFile previewFile,
            MultipartFile trailerFile,
            MultipartFile videoFile,
            List<Category> categories,
            List<Subscribe> subscribes
    ) throws IOException {

        Access access = accessService.save(price, accessType);
        String preview = fileService.savePreview(previewFile);
        String trailer = fileService.saveTrailer(trailerFile);
        if (!videoFile.isEmpty()) {
            String video = fileService.saveFilm(videoFile);
            film.setVideo(video);
        }

        film.setAccess(access);
        film.setPreview(preview);
        film.setTrailer(trailer);
        film.setCategories(new HashSet<>(categories));
        if (subscribes != null) film.setSubscribes(new HashSet<>(subscribes));

        return filmRepo.save(film);
    }

    public void delete(Long id) {
        Film film = getFilmById(id);

        fileService.deletePreview(film.getPreview());
        fileService.deleteTrailer(film.getTrailer());
        if (film.getVideo() != null) fileService.deleteFilm(film.getVideo());
        film.getCategories().clear();
        film.getSubscribes().clear();
        film.getRating().clear();

        filmRepo.deleteById(id);
    }

    public Map<Category, List<Film>> filterFilms(int page, int size) {
        Map<Category, List<Film>> filteredFilms = new HashMap<>();
        categoryService.getCategories().forEach(category -> {
            List<Film> filmsByCategory = filmRepo.findAllByCategoriesContains(category, PageRequest.of(page, size, Sort.Direction.DESC, "id"));
            if (filmsByCategory != null && !filmsByCategory.isEmpty()) {
                filteredFilms.put(category, filmsByCategory);
            }
        });
        return filteredFilms;
    }

    public Map<Category, List<Film>> filterFilms(int page) {
        return filterFilms(page, PAGE_SIZE);
    }

    public List<Film> getFilmsByCategory(Category category, int page) {
        return filmRepo.findAllByCategoriesContains(category, PageRequest.of(page, PAGE_SIZE, Sort.Direction.DESC, "id"));
    }

    public List<Film> getFilmsBySubscribe(Subscribe subscribe, int page) {
        return filmRepo.findAllBySubscribesContains(subscribe, PageRequest.of(page, PAGE_SIZE, Sort.Direction.DESC, "id"));
    }

    public List<Film> getFilmsWithoutCategory(Category category) {
        return filmRepo.findAllByCategoriesNotContains(category);
    }

    public List<Film> getFilmsWithoutSubscribe(Subscribe subscribe) {
        return filmRepo.findAllBySubscribesNotContains(subscribe);
    }

    public List<Film> getFilmsById(List<Long> ids) {
        if (ids == null) return null;
        return filmRepo.findAllByIdIn(ids);
    }

    public List<Film> getFilmsWithOneCategory(Long categoryId) {
        return filmRepo.findFilmsWithOneCategory(categoryId);
    }

    public Film update(
            Film film,
            AccessType accessType,
            Double price,
            MultipartFile previewFile,
            MultipartFile trailerFile,
            MultipartFile videoFile,
            List<Category> categories,
            List<Subscribe> subscribes
    ) throws IOException {

        Film filmFromBD = getFilmById(film.getId());

        filmFromBD.setTitle(film.getTitle());
        filmFromBD.setDescription(film.getDescription());
        filmFromBD.setAgeLimit(film.getAgeLimit());
        filmFromBD.setReleaseDate(film.getReleaseDate());
        if (!previewFile.isEmpty()) {
            fileService.deletePreview(filmFromBD.getPreview());
            filmFromBD.setPreview(fileService.savePreview(previewFile));
        }
        if (!trailerFile.isEmpty()) {
            fileService.deleteTrailer(filmFromBD.getTrailer());
            filmFromBD.setTrailer(fileService.saveTrailer(trailerFile));
        }
        if (filmFromBD.getAccess().getType() != accessType) {
            filmFromBD.getAccess().setType(accessType);
            if (accessType == AccessType.FREE && filmFromBD.getAccess().getPrice() != 0) {
                filmFromBD.getAccess().setPrice(price);
            }
        }
        if (!filmFromBD.getAccess().getPrice().equals(price)) {
            if (filmFromBD.getAccess().getType() == AccessType.BUY) {
                filmFromBD.getAccess().setPrice(price);
            }
        }
        if (!videoFile.isEmpty()) {
            if (filmFromBD.getVideo() != null) fileService.deleteFilm(filmFromBD.getVideo());
            filmFromBD.setVideo(fileService.saveFilm(videoFile));
        }
        filmFromBD.setCategories(new HashSet<>(categories));
        if (subscribes != null) filmFromBD.setSubscribes(new HashSet<>(subscribes));
        else filmFromBD.setSubscribes(null);

        return filmRepo.save(filmFromBD);
    }

    public Film update(Film film) {
        return filmRepo.save(film);
    }

    public Boolean hasUserAccess(Long filmId, Long userId) {
        if (filmId == null || userId == null) return false;
        return filmRepo.hasUserAccess(filmId, userId);
    }

    public Boolean hasUsers(Long filmId) {
        if (filmId == null) return false;
        return filmRepo.hasUsers(filmId);
    }

    public void saveRating(Film film, Rating rating, Integer value, User user) {
        if (rating != null) {
            if (!rating.getValue().equals(value)) {
                rating.setValue(value);
                film.getRating().add(rating);
                update(film);
            }
        } else {
            rating = ratingService.save(new Rating(film, user, value));
            film.getRating().add(rating);
            update(film);
        }
    }
}
