package kodlama.io.rentACar.business.concretes;

import java.util.List;

import org.springframework.stereotype.Service;

import kodlama.io.rentACar.business.abstracts.BrandService;
import kodlama.io.rentACar.business.requests.CreateBrandRequest;
import kodlama.io.rentACar.business.requests.UpdateBrandRequest;
import kodlama.io.rentACar.business.responses.GetAllBrandsResponse;
import kodlama.io.rentACar.business.responses.GetByİdBrandResponse;
import kodlama.io.rentACar.business.rules.BrandBusinessRules;
import kodlama.io.rentACar.core.utilities.mappers.ModelMapperService;
import kodlama.io.rentACar.dataAccess.abstracts.BrandRepository;
import kodlama.io.rentACar.entities.Brand;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class BrandManager implements BrandService{
	private BrandRepository brandRepository;
	private ModelMapperService modelMapperService;
	private BrandBusinessRules brandBusinessRules;
	


	@Override
	public List<GetAllBrandsResponse> getAll() {
		
		List<Brand>brands=brandRepository.findAll();
		List<GetAllBrandsResponse>brandsResponses=brands.stream()
				.map(brand->this.modelMapperService.forResponses().map(brand,GetAllBrandsResponse.class)).toList();
		return brandsResponses;
	}

	@Override
	public void add(CreateBrandRequest createBrandRequest) {
		this.brandBusinessRules.checkIfBrandNameExists(createBrandRequest.getName());
		Brand brand=this.modelMapperService.forRequest().map(createBrandRequest, Brand.class);
this.brandRepository.save(brand);		
	}

	@Override
	public GetByİdBrandResponse getByİd(int id) {
		Brand brand =this.brandRepository.findById(id).orElseThrow();
		GetByİdBrandResponse response=this.modelMapperService.forResponses().map(brand, GetByİdBrandResponse.class);
		return response;
	}

	@Override
	public void update(UpdateBrandRequest updateBrandRequest) {
		Brand brand=this.modelMapperService.forRequest().map(updateBrandRequest, Brand.class);
		this.brandRepository.save(brand);
	}

	@Override
	public void delete(int id) {
		this.brandRepository.deleteById(id);
		
	}

}
