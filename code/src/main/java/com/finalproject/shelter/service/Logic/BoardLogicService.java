package com.finalproject.shelter.service.Logic;

import com.finalproject.shelter.model.entity.Board;
import com.finalproject.shelter.repository.BoardRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class BoardLogicService {

    @Autowired
    private BoardRepository boardRepository;

    private Board newboard;
    private Long ids;
    private Board board1;
    private int view;

    public Page<Board> readAll(String id,String select, String searchText, Pageable pageable){

        if (select.equals("title")){
            Page<Board> boards = boardRepository.findBoardByCategoryIdAndTitleContainingAndContentsContainingOrderByRegisteredAtDesc
                    (Long.parseLong(id),searchText,"",pageable);
            if (boards!=null){
                return boards;
            }
        }else{
            Page<Board> boards = boardRepository.findBoardByCategoryIdAndTitleContainingAndContentsContainingOrderByRegisteredAtDesc
                    (Long.parseLong(id),"",searchText,pageable);
            if (boards!=null){
                return boards;
            }
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

    public Board readBoardview(String id, String viewboard){

        view = Integer.parseInt(viewboard);

        if (view>1 || view<0){
            view = 1;
        }

        Optional<Board> board = boardRepository.findBoardById(Long.parseLong(id));

        board.ifPresent(select->{
            select.setViewBoard(select.getViewBoard()+view);
            board1 = boardRepository.save(select);
        });
        return board1;
    }



    public List<Board> bestweekview(String id){
        List<Board> weekview = boardRepository.findTop5ByCategoryIdAndRegisteredAtBetweenOrderByViewBoardDesc
                (Long.parseLong(id), LocalDate.now().minusDays(3),LocalDate.now().plusDays(4));
        if (weekview!=null){
            return weekview;
        }else {
            return null;
        }
    }
    public List<Board> bestmonthview(String id){
        List<Board> monthview = boardRepository.findTop5ByCategoryIdAndRegisteredAtBetweenOrderByViewBoardDesc
                (Long.parseLong(id), LocalDate.now().minusDays(15),LocalDate.now().plusDays(15));
        if (monthview!=null){
            return monthview;
        }else {
            return null;
        }
    }





    public String deleteid(String id){
        Optional<Board> board = boardRepository.findBoardById(Long.parseLong(id));

        board.ifPresent(select->{
            ids = select.getCategory().getId();
            boardRepository.delete(select);
        });

        return String.valueOf(ids);
    }

    public Board postservice(Board board){
        Optional<Board> board1 = boardRepository.findBoardById(board.getId());

        board1.ifPresent(select->{
            select.setTitle(board.getTitle())
                    .setContents(board.getContents());
            newboard = boardRepository.save(select);
        });
        if (board1.isEmpty()){
            newboard = boardRepository.save(board);
        }

        return newboard;
    }
}
