package service;

import dto.CategoryDTO;
import model.Category;
import repository.CategoryRepository;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    public CategoryService(CategoryRepository categoryRepository, ModelMapper modelMapper) {
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
    }

    public List<CategoryDTO> getAllCategories() {
        return categoryRepository.findAll().stream()
                .map(category -> modelMapper.map(category, CategoryDTO.class))
                .collect(Collectors.toList());
    }

    public CategoryDTO getCategoryById(UUID id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoria não encontrada"));
        return modelMapper.map(category, CategoryDTO.class);
    }

    public CategoryDTO createCategory(CategoryDTO categoryDTO) {
        Category category = modelMapper.map(categoryDTO, Category.class);
        category.setIdCategory(UUID.randomUUID());
        category = categoryRepository.save(category);
        return modelMapper.map(category, CategoryDTO.class);
    }

    public CategoryDTO updateCategory(UUID id, CategoryDTO categoryDTO) {
        Category existing = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoria não encontrada"));

        existing.setName(categoryDTO.getName());
        existing.setDescription(categoryDTO.getDescription());

        return modelMapper.map(categoryRepository.save(existing), CategoryDTO.class);
    }

    public void deleteCategory(UUID id) {
        categoryRepository.deleteById(id);
    }
}
