package com.finalproject.shelter.service.Logic;

import com.finalproject.shelter.model.entity.Board;
import com.finalproject.shelter.repository.BoardRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class BoardLogicService {

    @Autowired
    private BoardRepository boardRepository;

    Long ids;
    Board board1;

    public Page<Board> readAll(String id, Pageable pageable){

        Page<Board> boards = boardRepository.findAllByCategoryId(Long.parseLong(id),pageable);

        if (boards!=null){
            return boards;
        }
        return null;
    }

    public Board readCategory(String id){
        List<Board> board = boardRepository.findBoardByCategoryId(Long.parseLong(id));
        board1 = board.get(0);

        return board1;
    }

    public Board readBoard(String id){

        Optional<Board> board = boardRepository.findBoardById(Long.parseLong(id));

        board.ifPresent(select->{
            board1 = select;
        });
        return board1;
    }

    public String deleteid(String id){
        Optional<Board> board = boardRepository.findBoardById(Long.parseLong(id));

        board.ifPresent(select->{
            ids = select.getCategory().getId();
            boardRepository.delete(select);
        });

        return String.valueOf(ids);
    }
}
