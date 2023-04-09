package com.humber.group4.citycomplaintsystem.dao.natl;

import com.humber.group4.citycomplaintsystem.models.mvd.Complaint;
import com.humber.group4.citycomplaintsystem.models.natl.Reply;

import java.util.List;

public interface ReplyDao {
    void create(Reply reply);

    List<Complaint> listByComplaint(Complaint read);

    void delete(Long id);

    Reply readById(long id);
}
