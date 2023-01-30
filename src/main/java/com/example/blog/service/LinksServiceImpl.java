package com.example.blog.service;

import com.example.blog.constant.Messages;
import com.example.blog.entity.Links;
import com.example.blog.repository.LinksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class LinksServiceImpl implements  LinksService{
    private  final LinksRepository linksRepository;

    @Autowired
    public LinksServiceImpl(LinksRepository linksRepository) {
        this.linksRepository = linksRepository;
    }

    @Override
    public List<Links> getAll() {
        return linksRepository.findAllBy();
    }

    @Override
    public Messages add(Links links) {
        linksRepository.save(links);
        return Messages.SUCCESS;
    }

    @Override
    public Messages update(Links links) {
        linksRepository.save(links);
        return Messages.SUCCESS;
    }

    @Override
    public Messages remove(UUID uuid) {
        Links links=linksRepository.findById(uuid).orElse(null);
        if(links==null){
            return  Messages.WRONG_ID;
        }
        linksRepository.delete(links);
        return Messages.SUCCESS;
    }
}
