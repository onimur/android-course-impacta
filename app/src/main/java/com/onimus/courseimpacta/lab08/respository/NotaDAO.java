package com.onimus.courseimpacta.lab08.respository;

import com.onimus.courseimpacta.lab08.domain.model.Nota;

import java.util.List;

public interface NotaDAO {
    String TAG = "NOTA DAO";

    void insert(Nota nota);

    void updateDone(Long id);

    void update(Nota nota);

    void delete(Nota nota);

    List<Nota> selectLost();

    List<Nota> selectToDo();

    List<Nota> selectDone();
}

