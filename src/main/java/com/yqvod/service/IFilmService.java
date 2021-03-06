package com.yqvod.service;

import com.github.pagehelper.PageInfo;
import com.yqvod.common.ServerResponse;
import com.yqvod.pojo.Film;
import com.yqvod.vo.FilmDetailVo;

public interface IFilmService {
    ServerResponse saveOrUpdateFilm(Film film);
    ServerResponse<String> setSaleStatus(Integer filmId,Integer status);
    ServerResponse<FilmDetailVo> manageFilmDetail(Integer filmId);
    ServerResponse getFilmList(int pageNum,int pageSize);
    ServerResponse<PageInfo> searchFilm(String filmName, Integer filmId, int pageNum, int pageSize);
    ServerResponse<FilmDetailVo> getFilmDetail(Integer filmId);
    ServerResponse<PageInfo> getFilmByKeywordCategory(String keyword,Integer categoryId,int pageNum,int pageSize,String orderBy);
    ServerResponse<String> takeCount(Integer filmId);
}
