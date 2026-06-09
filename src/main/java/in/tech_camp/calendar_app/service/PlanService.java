package in.tech_camp.calendar_app.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        plan.setStartDate(form.getStartDate());
        plan.setEndDate(form.getEndDate());
        plan.setEventType(form.getEventType());
        plan.setDescription(form.getDescription() != null ? form.getDescription() : "");
        
        // Repository側では、MyBatisの #{}（プレースホルダー）を使っているためSQLインジェクション対策もバッチリです
        planRepository.insert(plan);
        return plan; // 保存されたデータ（IDが振られたもの）を返す
    }

    @Transactional
    public void deletePlan(Integer id) {
        planRepository.deleteById(id);
    }
}