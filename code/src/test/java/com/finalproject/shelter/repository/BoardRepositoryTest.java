package com.finalproject.shelter.repository;


import com.finalproject.shelter.ShelterApplicationTests;
import com.finalproject.shelter.model.entity.Board;
import com.finalproject.shelter.model.entity.Category;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


public class BoardRepositoryTest extends ShelterApplicationTests {

    @Autowired
    private BoardRepository boardRepository;

    @Test
    public void create(){
        try {
            for (int i=101; i<102; i++) {
                Board board = Board.builder()
                        .title("Test"+i)
                        .nickname("승헌")
                        .contents("testtestssss"+i)
                        .category(Category.builder().id(1L).build())
                        .build();
                Board newboard = boardRepository.save(board);
                Assertions.assertTrue(newboard != null);}
        }catch(Exception e) {
                System.out.println("error" + e);
        }
    }

    @Test
    @Transactional
    public void boardview() throws Exception{
        List<Board> boardList = boardRepository.findTop5ByCategoryIdAndRegisteredAtBetweenOrderByViewBoardDesc
                (3L,LocalDate.now().minusDays(15),LocalDate.now().plusDays(15));

        if(boardList!=null){
            boardList.stream().forEach(select->{
                System.out.println(select);
            });
        }
    }

    @Test
    public void boardlist() throws Exception{
        List<Board> board = boardRepository.findBoardByCategoryId(Long.parseLong("1"));
        Assertions.assertTrue(board.isEmpty());
    }
//
    @Test
    public void readboardid() throws Exception{
        Optional<Board> board = boardRepository.findBoardById(Long.parseLong("1"));

        Board[] board1 = new Board[1];

        board.ifPresent(select->{
            board1[0] = select;
        });
        System.out.println(board1[0]);
    }

    @Test
    public void readCategory(){
        List<Board> board = boardRepository.findBoardByCategoryId(Long.parseLong("3"));
        Board board1 = board.get(0);

        System.out.println(board1);
    }

    @Test
    public void readAll(){
        List<Board> boards = boardRepository.findAll();
        boards.stream().forEach(select->{
            System.out.println(select.getContents());
        });
    }


    @Test
    @Transactional
    public void update(){
        Optional<Board> board = boardRepository.findBoardById(4L);
        Assertions.assertTrue(board.isPresent());

        board.ifPresent(select->{
            select
                    .setTitle("spring 이해")
                    .setNickname("asas")
                    .setContents("Spring도 어렵다.");
            boardRepository.save(select);
        });
    }

    @Test
    @Transactional
    public void delete(){
        Optional<Board> board = boardRepository.findBoardById(4L);
        Assertions.assertTrue(board.isPresent());

        board.ifPresent(select->{
            boardRepository.delete(select);
        });

        Optional<Board> otherboard = boardRepository.findBoardById(4L);
        Assertions.assertFalse(otherboard.isPresent());

    }
}
