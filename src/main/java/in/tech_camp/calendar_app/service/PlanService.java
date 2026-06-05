package in.tech_camp.calendar_app.service;

import java.util.List;

import org.springframework.stereotype.Service;

import in.tech_camp.calendar_app.entity.PlanEntity;
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

    // 3. 「新しい予定を保存して！」と言われたときの処理
    public void createPlan(PlanEntity plan) {
        // リポジトリに「この予定をDBに保存して」と丸投げして実行する
        planRepository.insert(plan);
    }
}