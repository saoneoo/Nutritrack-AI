package sawan.dev.NutriTrackAI.controller;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sawan.dev.NutriTrackAI.dto.requestDto.FoodItemRequestDTO;
import sawan.dev.NutriTrackAI.dto.responseDto.FoodItemResponseDTO;
import sawan.dev.NutriTrackAI.entity.FoodItem;
import sawan.dev.NutriTrackAI.repository.FoodItemRepository;
import sawan.dev.NutriTrackAI.service.FoodItemService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/food-items")
@RequiredArgsConstructor
public class FoodItemController {

    private final FoodItemRepository foodItemRepository;
    private final ModelMapper modelMapper;
    private  final FoodItemService foodItemService;

    @PostMapping
    public ResponseEntity<FoodItemResponseDTO> createFoodItem(@RequestBody FoodItemRequestDTO requestDTO) {
        // Call AI Nutrition Service instead of user-provided calories
        FoodItem foodItem = foodItemService.getOrCreateFoodItem(requestDTO.getName());

        // Save food item to DB
        FoodItem saved = foodItemRepository.save(foodItem);

        // Map Entity → ResponseDTO
        FoodItemResponseDTO responseDTO = modelMapper.map(saved, FoodItemResponseDTO.class);

        return ResponseEntity.ok(responseDTO);
    }


    // ✅ Get All Food Items
    @GetMapping
    public ResponseEntity<List<FoodItemResponseDTO>> getAllFoodItems() {
        List<FoodItemResponseDTO> responseList = foodItemRepository.findAll()
                .stream()
                .map(food -> modelMapper.map(food, FoodItemResponseDTO.class))
                .collect(Collectors.toList());

        return ResponseEntity.ok(responseList);
    }

    // ✅ Get Food Item by ID
    @GetMapping("/{id}")
    public ResponseEntity<FoodItemResponseDTO> getFoodItem(@PathVariable Long id) {
        FoodItem foodItem = foodItemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Food item not found"));

        FoodItemResponseDTO responseDTO = modelMapper.map(foodItem, FoodItemResponseDTO.class);

        return ResponseEntity.ok(responseDTO);
    }
}
