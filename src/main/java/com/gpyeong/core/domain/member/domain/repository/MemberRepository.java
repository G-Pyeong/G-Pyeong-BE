package com.gpyeong.core.domain.member.domain.repository;

import com.gpyeong.core.domain.member.domain.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, String> {
}
