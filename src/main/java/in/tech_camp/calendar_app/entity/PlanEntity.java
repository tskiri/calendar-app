package in.tech_camp.calendar_app.entity;

import java.time.LocalDate;

import in.tech_camp.calendar_app.EventType;
import lombok.Data;

@Data
public class PlanEntity {
  private Integer id;
  private String title;
  private EventType eventType;
  private LocalDate startDate;
  private LocalDate endDate;
  private String description;
}
