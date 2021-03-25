package com.finalproject.shelter.repository;


import com.finalproject.shelter.ShelterApplicationTests;
import com.finalproject.shelter.model.entity.Board;
import com.finalproject.shelter.model.entity.Category;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BoardRepositoryTest extends ShelterApplicationTests {

    private static final Logger log = Logger.getLogger(BoardRepositoryTest.class.getName());

    @Autowired
    private BoardRepository boardRepository;

    @DisplayName("게시판 작성 테스트")
    @Test
    @Transactional
    public void create(){
        try {
            for (int i=40; i<50; i++) {
                Board board = Board.builder()
                        .title("Test"+i)
                        .nickname("승헌")
                        .contents("testtestssss"+i)
                        .category(Category.builder().id(5L).build())
                        .build();
                Board newboard = boardRepository.save(board);
                Assertions.assertTrue(newboard.equals(board));
            }
        }catch(Exception e) {
            log.info("error");
        }
    }

    @DisplayName("게시판 페이지뷰 테스트")
    @Test
    @Transactional
    public void pageview() throws Exception{
        Pageable pageable = Pageable.unpaged();
        Page<Board> boardPage = boardRepository.findBoardByCategoryIdAndTitleContainingAndContentsContainingOrderByRegisteredAtDescIdDesc
                (1L,"","",pageable);
        Assertions.assertFalse(boardPage.isEmpty());

        if(boardPage !=null){
            log.info(String.valueOf(boardPage.getTotalPages()));
            log.info(String.valueOf(boardPage.toString()));
        }
    }

    @DisplayName("게시판 리스트 조회 테스트")
    @Test
    @Transactional
    public void boardview() throws Exception{
        List<Board> boardList = boardRepository.findTop5ByCategoryIdAndRegisteredAtBetweenOrderByViewBoardDesc
                (3L,LocalDate.now().minusMonths(1),LocalDate.now());
        Assertions.assertFalse(boardList.isEmpty());

        if(boardList!=null){
            boardList.stream().forEach(select->{
                log.info(select.toString());
            });
        }
    }

    @DisplayName("게시판 리스트 작성 확인")
    @Test
    @Transactional
    public void boardlist() throws Exception{
        List<Board> board = boardRepository.findBoardByCategoryId(1L);
        Assertions.assertFalse(board.isEmpty());

        if (board!=null){
            board.stream().forEach(select->{
                log.info(select.toString());
            });
        }
    }
//

    @Test
    @Transactional
    public void readboardid() throws Exception{
        Optional<Board> board = boardRepository.findBoardById(1L);
        Assertions.assertTrue(board.isPresent());

        board.ifPresent(select->{
            log.info(select.toString());
        });
    }


    @DisplayName("카테고리 읽기 기능 테스트")
    @Test
    @Transactional
    public void readCategory(){
        List<Board> board = boardRepository.findBoardByCategoryId(3L);
        if (board.isEmpty()){
            log.info("존재하지 않음");
        }else{
            Board board1 = board.get(0);
            Assertions.assertTrue(board1.getCategory().getId().equals(3L));
        }
    }

    @DisplayName("게시판 읽기 기능")
    @Test
    @Transactional
    public void readAll(){
        List<Board> boards = boardRepository.findAll();
        boards.stream().forEach(select->{
            log.info(select.getContents());
        });
    }

    @DisplayName("게시판 수정 기능 테스트")
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

    @DisplayName("게시판 삭제 테스트")
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

    @DisplayName("게시판 글작성 링크 테스트 정규표현식")
    @Test
    @Transactional
    public void link(){
        Optional<Board> board = boardRepository.findBoardById(10L);
        Assertions.assertTrue(board.isPresent());

        board.ifPresent(select->{

            String REGEX = "\\b(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";

            Pattern pattern = Pattern.compile(REGEX);
            Matcher matcher = pattern.matcher(select.getContents());

            while(matcher.find()){
                select.setContents(select.getContents().replace(matcher.group(),"<a href="+matcher.group()+"></a>"));
                log.info(matcher.group());
                Assertions.assertTrue(select.getContents().contains("<a href="+matcher.group()+"></a>"));
            }

        });
    }
}
