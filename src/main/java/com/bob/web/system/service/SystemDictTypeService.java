package com.bob.web.system.service;

import com.bob.common.core.domain.Ztree;
import com.bob.web.system.domain.SystemDictData;
import com.bob.web.system.domain.SystemDictType;

import java.util.List;

/**
 * 字典 业务层
 *
 * @author bob
 */
public interface SystemDictTypeService {
    /**
     * 根据条件分页查询字典类型
     *
     * @param dictType 字典类型信息
     * @return 字典类型集合信息
     */
    List<SystemDictType> selectDictTypeList(SystemDictType dictType);

    /**
     * 根据所有字典类型
     *
     * @return 字典类型集合信息
     */
    List<SystemDictType> selectDictTypeAll();

    /**
     * 根据字典类型查询字典数据
     *
     * @param dictType 字典类型
     * @return 字典数据集合信息
     */
    List<SystemDictData> selectDictDataByType(String dictType);

    /**
     * 根据字典类型ID查询信息
     *
     * @param dictId 字典类型ID
     * @return 字典类型
     */
    SystemDictType selectDictTypeById(Long dictId);

    /**
     * 根据字典类型查询信息
     *
     * @param dictType 字典类型
     * @return 字典类型
     */
    SystemDictType selectDictTypeByType(String dictType);

    /**
     * 批量删除字典类型
     *
     * @param ids 需要删除的数据
     * @return 结果
     * @throws Exception 异常
     */
    int deleteDictTypeByIds(String ids);

    /**
     * 清空缓存数据
     */
    void clearCache();

    /**
     * 新增保存字典类型信息
     *
     * @param dictType 字典类型信息
     * @return 结果
     */
    int insertDictType(SystemDictType dictType);

    /**
     * 修改保存字典类型信息
     *
     * @param dictType 字典类型信息
     * @return 结果
     */
    int updateDictType(SystemDictType dictType);

    /**
     * 校验字典类型称是否唯一
     *
     * @param dictType 字典类型
     * @return 结果
     */
    String checkDictTypeUnique(SystemDictType dictType);

    /**
     * 查询字典类型树
     *
     * @param dictType 字典类型
     * @return 所有字典类型
     */
    List<Ztree> selectDictTree(SystemDictType dictType);
}
