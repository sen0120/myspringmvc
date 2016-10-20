package com.hus.mapper;

import com.hus.domain.StudentDo;
import com.hus.domain.StudentExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface Student3Mapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_student
     *
     * @mbggenerated
     */
    int countByExample(StudentExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_student
     *
     * @mbggenerated
     */
    int deleteByExample(StudentExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_student
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_student
     *
     * @mbggenerated
     */
    int insert(StudentDo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_student
     *
     * @mbggenerated
     */
    int insertSelective(StudentDo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_student
     *
     * @mbggenerated
     */
    List<StudentDo> selectByExampleWithRowbounds(StudentExample example, RowBounds rowBounds);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_student
     *
     * @mbggenerated
     */
    List<StudentDo> selectByExample(StudentExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_student
     *
     * @mbggenerated
     */
    StudentDo selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_student
     *
     * @mbggenerated
     */
    int updateByExampleSelective(@Param("record") StudentDo record, @Param("example") StudentExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_student
     *
     * @mbggenerated
     */
    int updateByExample(@Param("record") StudentDo record, @Param("example") StudentExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_student
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(StudentDo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_student
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(StudentDo record);
}