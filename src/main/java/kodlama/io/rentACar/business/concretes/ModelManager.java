package kodlama.io.rentACar.business.concretes;

import java.util.List;

import org.springframework.stereotype.Service;

import kodlama.io.rentACar.business.abstracts.ModelService;
import kodlama.io.rentACar.business.requests.CreateModelRequest;
import kodlama.io.rentACar.business.responses.GetAllModelsResponse;
import kodlama.io.rentACar.core.utilities.mappers.ModelMapperService;
import kodlama.io.rentACar.dataAccess.abstracts.ModelRepository;
import kodlama.io.rentACar.entities.Model;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ModelManager implements ModelService{
	
	private ModelRepository modelRepository;
	private ModelMapperService modelMapperService;
	
	@Override
	public List<GetAllModelsResponse> getAll() {
		List<Model>models=modelRepository.findAll();
		
		List<GetAllModelsResponse>modelsResponses=models.stream()
				.map(model->this.modelMapperService.forResponses().map(model,GetAllModelsResponse.class)).toList();
		return modelsResponses;
	}

	@Override
	public void add(CreateModelRequest createModelRequest) {
		Model model=this.modelMapperService.forRequest().map(createModelRequest, Model.class);
		this.modelRepository.save(model);
	}

}
