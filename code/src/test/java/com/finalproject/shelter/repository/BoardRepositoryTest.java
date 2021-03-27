package com.finalproject.shelter.repository;


import com.finalproject.shelter.ShelterApplicationTests;
import com.finalproject.shelter.model.entity.Board;
import com.finalproject.shelter.model.entity.Category;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@Slf4j
public class BoardRepositoryTest extends ShelterApplicationTests {

    @Autowired
    private BoardRepository boardRepository;

    @BeforeEach
    public void createBefore(){
        for (int i=0;i<11; i++) {
            Board board = Board.builder()
                    .title("Test"+i)
                    .nickname("승헌")
                    .contents("testtestssss")
                    .category(Category.builder().id(3L).build())
                    .build();
            Board newboard = boardRepository.save(board);
        }
    }

    @DisplayName("게시판 작성 테스트")
    @Test
    public void create(){

        Board board = Board.builder()
                .title("Test")
                .nickname("승헌")
                .contents("testtestssss")
                .category(Category.builder().id(5L).build())
                .build();

        Board newboard = boardRepository.save(board);

        Assertions.assertTrue(newboard.equals(board));

        assertThat(newboard.getTitle()).isEqualTo(board.getTitle());
        assertThat(newboard.getNickname()).isEqualTo(board.getNickname());
        assertThat(newboard.getContents()).isEqualTo(board.getContents());
        assertThat(newboard.getCategory()).isEqualTo(board.getCategory());
    }

    @DisplayName("게시판 페이지뷰 테스트")
    @Test
    public void pageView() {

        Pageable pageable = PageRequest.of(0,10);
        Page<Board> boardPage = boardRepository.findBoardByCategoryIdAndTitleContainingAndContentsContainingOrderByRegisteredAtDescIdDesc
                (3L,"","",pageable);

        int count = (int)(boardRepository.findBoardByCategoryId(3L).stream().count()-1)/10;
        Assertions.assertFalse(boardPage.isEmpty());

        if(boardPage !=null){
            assertThat(boardPage.getTotalPages()).isEqualTo(count+1);
            boardPage.stream().forEach(select->{
                assertThat(select.getCategory().getId()).isEqualTo(3L);
            });
        }else{
            log.error("board 없음");
        }
    }

    @DisplayName("게시판 리스트 조회 테스트")
    @Test
    public void findBoardIdTop5(){

        List<Board> boardList = boardRepository.findTop5ByCategoryIdAndRegisteredAtBetweenOrderByViewBoardDesc
                (1L,LocalDate.now().minusMonths(1),LocalDate.now());

        Assertions.assertFalse(boardList.isEmpty());
        Assertions.assertTrue(boardList.stream().count()<=5);

        if(boardList!=null){
            boardList.stream().forEach(select->{
                assertThat(select.getCategory().getId()).isEqualTo(1L);
                assertThat(select.getRegisteredAt()).isBetween(LocalDate.now().minusMonths(1),LocalDate.now());
            });
        }
    }

    @DisplayName("게시판 리스트 작성 확인")
    @Test
    public void findCategoryIdList() {
        List<Board> board = boardRepository.findBoardByCategoryId(1L);
        Assertions.assertFalse(board.isEmpty());

        if (board!=null){
            board.stream().forEach(select->{
                assertThat(select.getCategory().getId()).isEqualTo(1L);
            });
        }else{
            log.error("게시판 정보 없음");
        }
    }

    @DisplayName("레코드 board id 찾기 테스트")
    @Test
    public void findBoardId(){
        Optional<Board> board = boardRepository.findBoardById(4L);
        Assertions.assertTrue(board.isPresent());

        board.ifPresent(select->{
            assertThat(select.getId()).isEqualTo(4L);
        });
    }


    @DisplayName("카테고리 읽기 기능 테스트")
    @Test
    public void findCategoryId(){
        List<Board> board = boardRepository.findBoardByCategoryId(3L);
        if (board.isEmpty()){
            log.info("존재하지 않음");
        }else{
            Board board1 = board.get(0);
            Assertions.assertTrue(board1.getCategory().getId().equals(3L));
        }
    }

    @DisplayName("레코드 수정 테스트")
    @Test
    public void update(){
        Optional<Board> board = boardRepository.findBoardById(4L);
        Assertions.assertTrue(board.isPresent());

        board.ifPresent(select->{
            select
                    .setTitle("spring 이해")
                    .setNickname("asas")
                    .setContents("Spring도 어렵다.");
            Board newboard = boardRepository.save(select);
            assertThat(newboard.getTitle()).isEqualTo("spring 이해");
            assertThat(newboard.getNickname()).isEqualTo("asas");
            assertThat(newboard.getContents()).isEqualTo("Spring도 어렵다.");
        });


    }

    @DisplayName("게시판 삭제 테스트")
    @Test
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
    public void link(){
        Optional<Board> board = boardRepository.findBoardById(6L);
        Assertions.assertTrue(board.isPresent());

        board.ifPresent(select->{

            String REGEX = "\\b(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";

            Pattern pattern = Pattern.compile(REGEX);
            Matcher matcher = pattern.matcher(select.getContents());

            while(matcher.find()){
                select.setContents(select.getContents().replace(matcher.group(),"<a href="+matcher.group()+"></a>"));
                Assertions.assertTrue(select.getContents().contains("<a href="+matcher.group()+"></a>"));
            }

        });
    }
}
