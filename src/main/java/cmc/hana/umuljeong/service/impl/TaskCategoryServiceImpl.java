package cmc.hana.umuljeong.service.impl;

import cmc.hana.umuljeong.repository.TaskCategoryRepository;
import cmc.hana.umuljeong.service.TaskCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TaskCategoryServiceImpl implements TaskCategoryService {

    private TaskCategoryRepository taskCategoryRepository;


}
