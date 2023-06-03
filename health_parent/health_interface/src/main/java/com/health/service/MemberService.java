package com.health.service;


import com.health.entity.Member;

import java.util.List;

public interface MemberService {
    Member findByTelephone(String telephone);
    void add(Member member);
    List<Integer> findMemberCountByMonths(List<String> months);
}
