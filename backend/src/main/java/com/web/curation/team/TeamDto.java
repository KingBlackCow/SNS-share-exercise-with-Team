package com.web.curation.team;

import com.web.curation.files.Photo;
import com.web.curation.files.PhotoDto;
import com.web.curation.member.Member;
import com.web.curation.sport.Sport;
import com.web.curation.sport.SportDto;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Valid
@Builder
public class TeamDto {
    private String name;
    private String introduction;
    private String leader;
    private int memberId;
    private int sportId;
    private MultipartFile multipartFile;

    private Integer teamId;
    private int memberCount;
    private String imgPath;
    private LocalDateTime createDate;

    private Integer photoId;
    private PhotoDto photoDto;
    private SportDto sportDto;

}