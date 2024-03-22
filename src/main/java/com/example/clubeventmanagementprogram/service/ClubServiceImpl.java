package com.example.clubeventmanagementprogram.service;
import com.example.clubeventmanagementprogram.dao.ClubDAO;
import com.example.clubeventmanagementprogram.model.Club;

import java.util.List;

public class ClubServiceImpl implements ClubService{
    private ClubDAO clubDao;

    public ClubServiceImpl(ClubDAO clubDao) {
        this.clubDao = clubDao;
    }
    @Override
    public void addClub(Club club) {
        clubDao.saveClub(club);
    }

    @Override
    public void updateClub(Club club) {
        clubDao.updateClub(club);
    }

    @Override
    public void deleteClub(int clubId) {
        clubDao.deleteClub(clubId);
    }

    @Override
    public Club getClubById(int id) {
        return clubDao.getClub(id);
    }

    @Override
    public List<Club> getAllClubs() {
        return clubDao.getAllClubs();
    }
}
