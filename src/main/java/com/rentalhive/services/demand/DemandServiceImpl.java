package com.rentalhive.services.demand;

import com.rentalhive.models.dto.DemandRequestDTO;
import com.rentalhive.models.dto.DemandResponseDTO;
import com.rentalhive.models.dto.EquipmentDemandRequestDTO;
import com.rentalhive.models.entities.Demand;
import com.rentalhive.models.entities.Equipment;
import com.rentalhive.models.entities.EquipmentDemand;
import com.rentalhive.models.enums.Status;
import com.rentalhive.repositories.DemandRepository;
import com.rentalhive.repositories.EquipmentDemandRepository;
import com.rentalhive.repositories.EquipmentRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.MethodArgumentNotValidException;

import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
@Service
@Transactional()
public class DemandServiceImpl implements DemandService {
    DemandRepository demandRepository;
    EquipmentRepository equipmentRepository;
    EquipmentDemandRepository equipmentDemandRepository;


    public DemandServiceImpl(DemandRepository demandRepository, EquipmentRepository equipmentRepository, EquipmentDemandRepository equipmentDemandRepository) {
        this.demandRepository = demandRepository;
        this.equipmentRepository = equipmentRepository;
        this.equipmentDemandRepository = equipmentDemandRepository;

    }
    @Override
    public DemandResponseDTO makeDemand(@Valid DemandRequestDTO demandRequestDTO){
        List<Long> equipmentIds = demandRequestDTO.equipmentDemands().stream().map(EquipmentDemandRequestDTO::equipmentId).toList();
        List<Equipment> equipmentList = equipmentRepository.findAllByIdIn(equipmentIds);
        if(equipmentList.size() == equipmentIds.size() ){
            Demand demand = demandRequestDTO.toDemand();
            List<EquipmentDemand> equipmentDemands = demandRequestDTO.equipmentDemands().stream().map(equipmentDemandRequestDTO -> equipmentDemandRequestDTO.toEquipmentDemand(demand, equipmentList.get(equipmentIds.indexOf(equipmentDemandRequestDTO.equipmentId())))).toList();
            demand.setEquipmentDemands(equipmentDemands);
            return DemandResponseDTO.fromDemand(demandRepository.save(demand));
        }else{
            throw new RuntimeException("Some Equipments not found");
        }
    }
    public List<DemandResponseDTO> getAllDemands(){
        return demandRepository.findAll().stream().map(DemandResponseDTO::fromDemand).toList();
    }
    public Boolean validateDemand(Long demandId){
        if(demandId == null){
            throw new IllegalArgumentException("Demand ID cannot be null");
        }
        Demand demand = demandRepository.findById(demandId).orElseThrow(() -> new RuntimeException("Demand not found"));
        demand.getEquipmentDemands().forEach(
                equipmentDemand -> {
                    List<EquipmentDemand> equipmentDemands = equipmentDemandRepository.getEquipmentDemandByIdAndRange(
                            Status.ACCEPTED,
                            equipmentDemand.getEquipment().getId(),
                            equipmentDemand.getStartDate(),
                            equipmentDemand.getEndDate()
                    );
                    if(equipmentDemands.size() > 0){
                        throw new RuntimeException("This Equipment "+equipmentDemand.getEquipment().getSerialNumber()+" not available for now");
                    }
                }
        );
        return true;
    }
    public DemandResponseDTO acceptDemand(Long demandId){
        validateDemand(demandId);
        Demand demand = demandRepository.findById(demandId).orElseThrow(() -> new RuntimeException("Demand not found"));
        demand.setStatus(Status.ACCEPTED);
        return DemandResponseDTO.fromDemand(demandRepository.save(demand));
    }
    public DemandResponseDTO rejectDemand(Long demandId){
        Demand demand = demandRepository.findById(demandId).orElseThrow(() -> new RuntimeException("Demand not found"));
        demand.setStatus(Status.REJECTED);
        return DemandResponseDTO.fromDemand(demandRepository.save(demand));
    }
    public DemandResponseDTO updateDemand(Long demandId, @Valid DemandRequestDTO demandRequestDTO){
        demandRepository.findById(demandId).orElseThrow(() -> new RuntimeException("Demand not found"));
        List<Long> equipmentIds = demandRequestDTO.equipmentDemands().stream().map(EquipmentDemandRequestDTO::equipmentId).toList();
        List<Equipment> equipmentList = equipmentRepository.findAllByIdIn(equipmentIds);
        if(equipmentList.size() == equipmentIds.size() ){
            Demand demand = demandRequestDTO.toDemand();
            demand.setId(demandId);
            List<EquipmentDemand> equipmentDemands = demandRequestDTO.equipmentDemands().stream().map(equipmentDemandRequestDTO -> equipmentDemandRequestDTO.toEquipmentDemand(demand, equipmentList.get(equipmentIds.indexOf(equipmentDemandRequestDTO.equipmentId())))).toList();
            demand.setEquipmentDemands(equipmentDemands);
            return DemandResponseDTO.fromDemand(demandRepository.save(demand));
        }else{
            throw new RuntimeException("Some Equipments not found");
        }
    }


}
