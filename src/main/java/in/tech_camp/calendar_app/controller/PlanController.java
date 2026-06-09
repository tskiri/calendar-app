package in.tech_camp.calendar_app.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import in.tech_camp.calendar_app.entity.PlanEntity;
import in.tech_camp.calendar_app.form.PlanForm;
import in.tech_camp.calendar_app.service.PlanService;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.hibernate.validator.internal.engine.groups.ValidationOrder;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("/api/plans")
@RequiredArgsConstructor
public class PlanController {

  private final PlanService planService;

  // 全予定の取得
  @GetMapping("/")
  public ResponseEntity<?> getplans() {
    try {
      List<PlanEntity> plans = planService.getPlans();
      return ResponseEntity.ok().body(plans);
    } catch (Exception e) {
      e.printStackTrace(); // 開発者のためのエラー詳細表示
      return ResponseEntity.internalServerError().body(Map.of("messages", List.of("予定の取得に失敗しました")));
    }
  }
  
  // 予定の作成
  @PostMapping("/")
  public ResponseEntity<?> createPlan(@RequestBody @Validated(ValidationOrder.class) PlanForm planForm, BindingResult result) {
    
    // バリデーションエラーがある場合（404 Bad Request）
    if (result.hasErrors()) {
      List<String> errorMessages = result.getAllErrors().stream()
      .map(DefaultMessageSourceResolvable::getDefaultMessage)
      .collect(Collectors.toList()); 
      return ResponseEntity.badRequest().body(Map.of("messages", errorMessages));
    }

    // 予定の保存処理
    try {
      PlanEntity savedPlan = planService.createPlan(planForm); 
      return ResponseEntity.ok().body(savedPlan);
    } catch (Exception e) {
      e.printStackTrace();
      return ResponseEntity.internalServerError().body(Map.of("messages", List.of("予定の保存に失敗しました")));
    }
  }

  // 予定の削除
  @DeleteMapping("/{id}")
  public ResponseEntity<?> deletePlan(@PathVariable("id") Integer id) {
    try {
      planService.deletePlan(id);
      return ResponseEntity.ok().body(Map.of(
        "Message", "予定を削除しました", "deleteId", id
      ));
    } catch (Exception e) {
      e.printStackTrace();
      return ResponseEntity.internalServerError().body(Map.of("messages", List.of("予定の削除に失敗しました")));
    }
  }
}
