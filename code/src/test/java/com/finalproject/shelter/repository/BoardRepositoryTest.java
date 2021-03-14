package com.finalproject.shelter.repository;


import com.finalproject.shelter.ShelterApplicationTests;
import com.finalproject.shelter.model.entity.Board;
import com.finalproject.shelter.model.entity.Category;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


public class BoardRepositoryTest extends ShelterApplicationTests {

    @Autowired
    private BoardRepository boardRepository;

    @Test
    public void create() throws Exception {
        for (int i=10; i<30; i++) {
            Board board = Board.builder()
                    .title("Test"+i)
                    .nickname("asas")
                    .contents("testtest"+i)
                    .category(Category.builder().id(16L).build())
                    .build();
            Board newboard = boardRepository.save(board);
            System.out.println(newboard);
        }
    }

    @Test
    public void boardlist() throws Exception{
        List<Board> boardList = boardRepository.findBoardByCategoryIdAndTitleContainingAndContentsContaining
                (3L,"test","");
        if(boardList!=null){
            boardList.stream().forEach(select->{
                System.out.println(select);
            });
        }
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
