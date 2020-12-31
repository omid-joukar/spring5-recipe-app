package omid.springframework.services;

import lombok.extern.slf4j.Slf4j;
import omid.springframework.domain.Recipe;
import omid.springframework.repositories.RecipeRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;

@Slf4j
@Service
public class ImageServiceImpl implements ImageService{
    private final RecipeRepository recipeRepository;

    public ImageServiceImpl(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @Override
    @Transactional
    public void saveImageFile(Long id, MultipartFile file) {
        try {
            Recipe recipe = recipeRepository.findById(id).get();
            Byte[] bytesObject = new Byte[file.getBytes().length];
            int i = 0 ;
            for (byte b : file.getBytes()){
                bytesObject[i++] = b;
            }
            recipe.setImage(bytesObject);
            recipeRepository.save(recipe);
            log.debug("recived a file");
        } catch (IOException e) {
            log.error("Error occured: "+ e);
            e.printStackTrace();
        }
    }
}
