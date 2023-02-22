package com.home.project.AssignmentSubmissionDemoApp.service;

import com.home.project.AssignmentSubmissionDemoApp.dto.CommentDto;
import com.home.project.AssignmentSubmissionDemoApp.entity.Assignment;
import com.home.project.AssignmentSubmissionDemoApp.entity.Comment;
import com.home.project.AssignmentSubmissionDemoApp.entity.User;
import com.home.project.AssignmentSubmissionDemoApp.repository.AssignmentRepository;
import com.home.project.AssignmentSubmissionDemoApp.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Set;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepo;
    @Autowired
    private AssignmentRepository assignmentRepo;

    public Comment save(CommentDto commentDto, User user) {
        Comment comment = new Comment();
        //TODO: try get Reference
        Assignment assignment = assignmentRepo.getById(commentDto.getAssignmentId());

        comment.setId(commentDto.getId());
        comment.setAssignment(assignment);
        comment.setText(commentDto.getText());
        comment.setCreatedBy(user);

        if (comment.getId() == null)
            comment.setCreatedDate(ZonedDateTime.now());
        else
            comment.setCreatedDate(commentDto.getCreatedDate());

        return commentRepo.save(comment);
    }

    public Set<Comment> getCommentsByAssignmentId(Long assignmentId) {
        Set<Comment> comments = commentRepo.findByAssignmentId(assignmentId);

        return comments;
    }

    public void delete(Long commentId) {
        commentRepo.deleteById(commentId);
    }
}
