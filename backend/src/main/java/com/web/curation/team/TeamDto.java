package com.web.curation.team;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.web.curation.model.Member;
import com.web.curation.sport.SportDto;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "Team")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@DynamicInsert
@Builder(builderMethodName = "TeamDtoBuilder")
public class TeamDto {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "team_id")
    private long teamId;

    private String name;

    private String introduction;

    private String leader;

    @Column(name = "member_count")
    @ColumnDefault("1")
    @JsonIgnore
    private int memberCount;

    @Column(name = "img_path")
    private String imgPath;

    @JsonIgnore
    @Column(columnDefinition="DATETIME default CURRENT_TIMESTAMP")
    private LocalDateTime createDate;

    //참조키
    @OneToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToOne
    @JoinColumn(name = "sport_id")
    private SportDto sportDto;

    public static TeamDtoBuilder builder(TeamDto teamDto){
        return TeamDtoBuilder()
                .name(teamDto.getName())
                .introduction(teamDto.getIntroduction())
                .leader(teamDto.getLeader())
                .imgPath(teamDto.getImgPath())
                .member(teamDto.getMember())
                .sportDto(teamDto.getSportDto());
    }


}
