package com.startrip.codebase.domain.state;

import com.startrip.codebase.dto.state.UpdateStateDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.UUID;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class State {
    @Id
    @Column(name = "state_id")
    private UUID stateId;

    @Column(name = "state_num")
    private Integer stateNum;

    public void update(UpdateStateDto dto) {
        this.stateNum = dto.getStateNum();
        switch (this.stateNum) {
            case 1:
                this.stateNum = 2; // 여행 수행
                break;
            case 2:
                this.stateNum = 3; // 여행 종료
                break;
            case 3:
                this.stateNum = 4; // 리뷰 알림
                break;
            case 4:
                this.stateNum = 5; // 리뷰 작성
                break;
            default:
                this.stateNum = 1; // 여행 계획
        }
    }
}
