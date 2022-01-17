package com.example.projetfestival.controller;


import com.example.projetfestival.entity.Artist;
import com.example.projetfestival.repository.ArtistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:5432")
@RestController
@RequestMapping("/api/artist")
public class ArtistController{
    private final ArtistRepository artistRepository;

    @Autowired
    public ArtistController(ArtistRepository artistRepository){
        this.artistRepository=artistRepository;
    }

    @GetMapping("")
    public List<Artist> getArtists(){
        List<Artist> artists = new ArrayList<>();
        try{
            artists = artistRepository.findAll();
        }catch (Exception e){
            System.out.println(e);
        }
        return artists;
    }


    @PostMapping("")
    public ResponseEntity<Artist> createArtist(@RequestBody String name, @RequestBody int passtime){
        try
        {
            Artist artist = artistRepository.save(new Artist(name,passtime));
            return new ResponseEntity<>(artist, HttpStatus.CREATED);
        }
        catch(Exception e)
        {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }



    @PutMapping("/{name},{passtime}")
    public ResponseEntity<Artist> updatePasstime(@PathVariable("name") String name,@PathVariable("passtime") int passtime)
    {

        List<Artist> artistsData = artistRepository.findAll();
        try{
            for(Artist a : artistsData)
            {
                if(a.getName().equals(name))
                {
                    Optional<Artist> artistData = artistRepository.findById((int) a.getId());
                    Artist artist = artistData.get();
                    artist.setName(name);
                    artist.setPass_time(passtime);
                    return new ResponseEntity<>(artistRepository.save(artist), HttpStatus.OK);

                }
                else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }catch (Exception e){
            System.out.println(e);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{name}")
    public ResponseEntity<HttpStatus> deleteLol(@PathVariable("name") String name)
    {
        List<Artist> artistsData = artistRepository.findAll();
        try{
            for(Artist a : artistsData)
            {
                if(a.getName().equals(name))
                {
                    Optional<Artist> artistData = artistRepository.findById((int) a.getId());
                    Artist artist = artistData.get();
                    artistRepository.delete(artist);
                    return new ResponseEntity<>( HttpStatus.OK);

                }
                else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }catch (Exception e){
            System.out.println(e);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
