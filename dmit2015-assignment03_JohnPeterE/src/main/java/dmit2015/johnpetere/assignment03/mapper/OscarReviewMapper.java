package dmit2015.johnpetere.assignment03.mapper;

import dmit2015.dto.OscarReviewDto;
import dmit2015.entity.OscarReview;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface OscarReviewMapper {

    OscarReviewMapper INSTANCE = Mappers.getMapper( OscarReviewMapper.class );

//    @Mappings({
//            @Mapping(target = "dtoPropetyName", source = "entity.entityPropertyName")
//    })
    OscarReviewDto toDto(OscarReview entity);

//    @Mappings({
//            @Mapping(target = "dtoPropetyName", source = "entityPropetyName")
//    })
    OscarReview toEntity(OscarReviewDto dto);

}

