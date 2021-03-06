package org.ylc.note.shardingsphere.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.ylc.note.shardingsphere.dto.SysLogDto;
import org.ylc.note.shardingsphere.entity.SysLog;
import org.ylc.note.shardingsphere.mapper.SysLogMapper;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 代码千万行，注释第一行，
 * 注释不规范，同事泪两行。
 *
 * @author YuLc
 * @version 1.0.0
 * @date 2020/7/24
 */
@Slf4j
@RequestMapping("/log")
@RestController
public class SysLogController {

    private final SysLogMapper sysLogMapper;

    public SysLogController(SysLogMapper sysLogMapper) {
        this.sysLogMapper = sysLogMapper;
    }

    @GetMapping("/rangeSearch")
    public ResponseEntity<List<SysLog>> rangeSearch(Integer beginMonth, Integer endMonth) {

        LocalDateTime begin = LocalDateTime.of(2020, beginMonth, 1, 0, 0);
        LocalDateTime end = LocalDateTime.of(2020, endMonth, 1, 0, 0);

        List<SysLog> rangeList = sysLogMapper.rangeSearch(begin, end);

        log.info("size: {}", rangeList.size());
        rangeList.forEach(o -> System.out.println(o.toString()));

        return ResponseEntity.ok(rangeList);
    }

    /**
     * 普通分页查询，注意观察实际的SQL语句
     */
    @GetMapping("/page")
    public ResponseEntity<List<SysLog>> page(Long page, Long size) {
        Long curIndex = (page - 1) * size;
        List<SysLog> pageList = sysLogMapper.page(curIndex, size);
        return ResponseEntity.ok(pageList);
    }

    /**
     * 普通分页查询，注意观察实际的SQL语句
     */
    @GetMapping("/pageById")
    public ResponseEntity<List<SysLog>> pageById(Long lastId, Long size) {
        List<SysLog> pageList = sysLogMapper.pageById(lastId, size);
        return ResponseEntity.ok(pageList);
    }

    @PostMapping("/newLog")
    public String newLog(@RequestBody SysLogDto dto) {

        LocalDateTime createTime = LocalDateTime.of(2020, dto.getMonth(), 7, 0, 0);
        SysLog log = new SysLog();
        log.setOperationId(dto.getOperationId());
        log.setValue(dto.getValue());
        log.setCreateTime(createTime);

        sysLogMapper.newLog(log);

        return "success";
    }

}
