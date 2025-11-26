package cn.cug.domain.basic.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class todoItemEntity {
    // 唯一标识
    private int id;

    // 标题（必填）
    private String title;

    // 描述（可选）
    private String description;

    // 完成状态
    private boolean isCompleted;

    // 创建时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
}
