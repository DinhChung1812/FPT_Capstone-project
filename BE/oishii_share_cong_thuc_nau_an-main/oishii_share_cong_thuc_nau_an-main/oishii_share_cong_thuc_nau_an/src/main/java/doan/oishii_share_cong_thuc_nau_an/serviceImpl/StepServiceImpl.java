package doan.oishii_share_cong_thuc_nau_an.serviceImpl;

import doan.oishii_share_cong_thuc_nau_an.common.vo.StepVo;
import doan.oishii_share_cong_thuc_nau_an.repositories.StepRepository;
import doan.oishii_share_cong_thuc_nau_an.service.StepService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StepServiceImpl implements StepService {

    @Autowired
    private StepRepository stepRepository;

    @Override
    public List<StepVo> findByFormulaID(Integer formulaId) {
        return stepRepository.findByFormulaID(formulaId);
    }
}
