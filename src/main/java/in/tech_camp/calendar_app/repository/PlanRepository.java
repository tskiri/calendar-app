package in.tech_camp.calendar_app.repository;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import in.tech_camp.calendar_app.entity.PlanEntity;

@Mapper
public interface PlanRepository {
  // 予定を全て取得
  @Select("SELECT * FROM plans")
  List<PlanEntity> findAll();

  // 予定をDBに保存
  @Insert("INSERT INTO plans(title, event_type, start_date, end_date, description) VALUES (#{title}, #{eventType}, #{startDate}, #{endDate}, #{description})")
  @Options(useGeneratedKeys = true, keyProperty = "id")
  void insert(PlanEntity plan);

  // 予定を削除
  @Delete("DELETE FROM plans WHERE id = #{id}")
  void deleteById(Integer id);

  // 予定を更新
  @Update("UPDATE plans SET title = #{title}, event_type = #{eventType}, start_date = #{startDate}, end_date = #{endDate}, description = #{description} WHERE id = #{id}")
  void update(PlanEntity plan);
}
