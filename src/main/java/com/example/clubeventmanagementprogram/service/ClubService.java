package com.example.clubeventmanagementprogram.service;

import com.example.clubeventmanagementprogram.model.Club;

import java.util.List;

public interface ClubService {
    void addClub(Club club);
    void updateClub(Club club);
    void deleteClub(int clubId);
    Club getClubById(int id);
    List<Club> getAllClubs();
}
