package com.health.dao;

import com.health.entity.Member;

public interface MemberDao {
    Member findByTelephone(String telephone);

    void add(Member member);

    Integer findMemberCountBeforeDate(String endTime);
}
