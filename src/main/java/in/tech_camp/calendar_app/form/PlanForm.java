package in.tech_camp.calendar_app.form;

import in.tech_camp.calendar_app.validation.ValidationPriority1;
import in.tech_camp.calendar_app.validation.ValidationPriority2;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class PlanForm {

    @NotBlank(groups = ValidationPriority1.class, message = "タイトルは必須です")
    @Size(max = 50, groups = ValidationPriority2.class, message = "タイトルは50文字以内で入力してください")
    private String title;

    @NotBlank(groups = ValidationPriority1.class, message = "開始日を入力してください")
    private String startDate;

    @NotBlank(groups = ValidationPriority1.class, message = "終了日を入力してください")
    private String endDate;

    @NotBlank(groups = ValidationPriority1.class, message = "イベントタイプを入力してください")
    private String eventType;

    private String description;
}
