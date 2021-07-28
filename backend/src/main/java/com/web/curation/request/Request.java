package com.web.curation.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.web.curation.member.Member;
import com.web.curation.team.TeamDto;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Request {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int requestId;
	
	@ManyToOne
	@JoinColumn(name = "Team_id", nullable = false)
	private TeamDto team;
	
	@ManyToOne
	@JoinColumn(name = "Member_id", nullable = false)
	private Member member;
	
	@JsonIgnore
	private int status;
	
	@CreationTimestamp
	@JsonIgnore
	private LocalDateTime createDate;
	
	@UpdateTimestamp
	@JsonIgnore
	private LocalDateTime updateDate;
}