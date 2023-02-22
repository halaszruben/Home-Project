package com.home.project.AssignmentSubmissionDemoApp.dto;

import com.home.project.AssignmentSubmissionDemoApp.entity.Assignment;
import com.home.project.AssignmentSubmissionDemoApp.enums.AssignmentEnum;
import com.home.project.AssignmentSubmissionDemoApp.enums.AssignmentStatusEnum;

public class AssignmentResponseDto {

    private Assignment assignment;
    private AssignmentEnum[] assignmentEnums = AssignmentEnum.values();
    private AssignmentStatusEnum[] statusEnums = AssignmentStatusEnum.values();

    public AssignmentResponseDto(Assignment assignment) {
        super();
        this.assignment = assignment;
    }

    public Assignment getAssignment() {
        return assignment;
    }

    public void setAssignment(Assignment assignment) {
        this.assignment = assignment;
    }

    public AssignmentEnum[] getAssignmentEnums() {
        return assignmentEnums;
    }

    public AssignmentStatusEnum[] getStatusEnums() {
        return statusEnums;
    }
}
