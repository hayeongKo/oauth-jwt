package member.repository;

import member.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<MemberEntity, Long> {
    boolean existsBySub(String sub);
    Optional<MemberEntity> findBySub(String sub);
}
