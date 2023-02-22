package com.home.project.AssignmentSubmissionDemoApp.repository;

import com.home.project.AssignmentSubmissionDemoApp.entity.Assignment;
import com.home.project.AssignmentSubmissionDemoApp.entity.Comment;
import com.home.project.AssignmentSubmissionDemoApp.entity.User;
import com.home.project.AssignmentSubmissionDemoApp.enums.AssignmentStatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Set;

public interface AssignmentRepository extends JpaRepository<Assignment, Long> {

    Set<Assignment> findByUser(User user);

    @Query("select a from Assignment a "
            + "where (a.status = 'submitted' " +
            "and (a.codeReviewer is null or " +
            "a.codeReviewer = :codeReviewer))"
            + "or a.codeReviewer = :codeReviewer")
    Set<Assignment> findByCodeReviewer(User codeReviewer);

}
