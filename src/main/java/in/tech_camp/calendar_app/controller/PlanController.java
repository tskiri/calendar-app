package in.tech_camp.calendar_app.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import in.tech_camp.calendar_app.entity.PlanEntity;
import in.tech_camp.calendar_app.service.PlanService;
import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("/api/plans")
@RequiredArgsConstructor
public class PlanController {
  private final PlanService planService;

  @GetMapping("/")
  public List<PlanEntity> getPlans() {
    List<PlanEntity> plans = planService.getPlans();
    return plans;
  }
  
  @PostMapping("/")
  public void createPlan(@RequestBody PlanEntity plan) {
    planService.createPlan(plan);
  }
}
