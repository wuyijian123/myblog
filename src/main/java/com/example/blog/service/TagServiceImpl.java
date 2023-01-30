package com.example.blog.service;

import com.example.blog.constant.Messages;
import com.example.blog.entity.Tag;
import com.example.blog.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
@Service
public class TagServiceImpl implements TagService {
    private  final TagRepository tagRepository;
    @Autowired
    public TagServiceImpl(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    @Override
    public List<Tag> getAll() {
        return tagRepository.findAll();
    }

    @Override
    public Tag getById(UUID tagId) {
        return tagRepository.findById(tagId).orElse(null);
    }

    @Override
    public Tag add(Tag tag) {
        return tagRepository.save(tag);
    }

    @Override
    public Tag update(Tag tag) {
        return tagRepository.save(tag);
    }

    @Override
    public List<Tag> getAllByArticleId(UUID uuid) {
        return tagRepository.findTagsByArticlesId(uuid);
    }

    @Override
    public Tag getByName(String name) {
        return tagRepository.findByName(name);
    }

    @Override
    public Messages remove(UUID uuid) {
        Tag tag=tagRepository.findById(uuid).orElse(null);
        if(tag==null){
            return  Messages.WRONG_ID;
        }
        tagRepository.delete(tag);
        return Messages.SUCCESS;
    }
}
