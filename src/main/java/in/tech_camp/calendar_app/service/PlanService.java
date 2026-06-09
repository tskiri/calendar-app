package in.tech_camp.calendar_app.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import in.tech_camp.calendar_app.EventType;
import in.tech_camp.calendar_app.entity.PlanEntity;
import in.tech_camp.calendar_app.form.PlanForm;
import in.tech_camp.calendar_app.repository.PlanRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PlanService {

    // 1. データベースを操作する「リポジトリ」という部品を定義する
    private final PlanRepository planRepository;

    // 2. 「予定を全件取ってきて！」と言われたときの処理
    public List<PlanEntity> getPlans() {
        // リポジトリに「DBから全件取ってきて」と丸投げし、その結果をそのまま返す
        return planRepository.findAll();
    }

    // コントローラーを薄くするため、FormからEntityへの変換（ビジネスロジック）をここで担当する
    @Transactional
    public PlanEntity createPlan(PlanForm form) {
        PlanEntity plan = new PlanEntity();
        plan.setTitle(form.getTitle());

        // PlanEntityに合うように、String型をLocalDate型にする
        plan.setStartDate(LocalDate.parse(form.getStartDate()));
        plan.setEndDate(LocalDate.parse(form.getEndDate()));

        // PlanEntityに合うように、String型をEnum型にする
        plan.setEventType(EventType.valueOf(form.getEventType()));

        plan.setDescription(form.getDescription() != null ? form.getDescription() : "");
        
        // Repository側では、MyBatisの #{}（プレースホルダー）を使っているためSQLインジェクション対策もバッチリです
        planRepository.insert(plan);
        return plan; // 保存されたデータ（IDが振られたもの）を返す
    }

    // 予定の削除
    @Transactional
    public void deletePlan(Integer id) {
        planRepository.deleteById(id);
    }

    // 予定の更新
    @Transactional
    public void updatePlan(Integer id, PlanForm form) {
        PlanEntity plan = new PlanEntity();
        plan.setId(id);
        plan.setTitle(form.getTitle());
        plan.setStartDate(LocalDate.parse(form.getStartDate()));
        plan.setEndDate(LocalDate.parse(form.getEndDate()));
        plan.setEventType(EventType.valueOf(form.getEventType()));
        plan.setDescription(form.getDescription() != null ? form.getDescription() : "");

        // リポジトリの更新処理を呼び出す
        planRepository.update(plan);
    }
}