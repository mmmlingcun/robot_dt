package com.meng.robot_dt.education.common;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.util.Collections;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

public class BaseController {

    /**
     * Converts origin page to target page.
     * Replaced with {@link BaseController#from(Page, Function)}
     * <br>
     *
     * @param origin
     * @param function function of type exchange
     * @param pageInfo
     * @param <T>      origin type
     * @param <R>      target type
     * @return page
     * @see {@link BaseController#from(Page, Function)}
     */
    @Deprecated
    public <T extends BaseEntity, R> Page<R> from(Page<T> origin, Function<T, R> function, Pageable pageInfo) {
        List<R> content = Collections.emptyList();
        if (!CollectionUtils.isEmpty(origin.getContent())) {
            content = origin.getContent().stream().map(function).collect(toList());
        }
        return new PageImpl<>(content, pageInfo, origin.getTotalElements());
    }

    /**
     * Converts element type of page.
     * <br>
     *
     * @param origin   source page
     * @param function convert function
     * @param <T>      element type
     * @param <R>      target element type
     * @return page with element type {@code R}
     */
    @NonNull
    public <T extends BaseEntity, R> Page<R> from(Page<T> origin, Function<T, R> function) {
        return origin.map(x -> function.apply(x));
    }

    public ResponseEntity getDownloadResponse(ByteArrayOutputStream outputStream, String fileName) throws UnsupportedEncodingException {
        ByteArrayResource resource = new ByteArrayResource(outputStream.toByteArray());
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" +
                        new String(fileName.getBytes("utf-8"), "iso8859-1"))
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .contentLength(resource.contentLength()).body(resource);
    }

    /**
     * Adds a resource.
     *
     * @param result
     * @param dto
     * @param function
     * @param <D>
     * @param <T>
     * @return response
     */
    public <D, T extends BaseEntity> ResponseEntity add(BindingResult result, D dto, Function<D, T> function) {
        return result.hasErrors() ? this.getErrorResponse(result) : this.getCreatedResponse(function, dto);
    }

    /**
     * Deletes record by {@code id} logically.
     * <br>
     *
     * @param id       identifier
     * @param function function used to delete record
     * @return boolean result of operation
     */
    public <ID extends Object, R extends BaseEntity> ResponseEntity deleteById(ID id, Function<ID, Boolean> function) {
        return function.apply(id) ? ResponseEntity.status(HttpStatus.OK).build() : ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    /**
     * Updates resource.
     *
     * @param result   binding result
     * @param dto      parameters and id
     * @param function function to process updating operation
     * @param <P>      dto's type
     * @return response
     */
    public <P> ResponseEntity update(BindingResult result, P dto, Function<P, Boolean> function) {
        if (result != null && result.hasErrors()) {
            return getErrorResponse(result);
        }
        if (function.apply(dto)) {
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    /**
     * Finds all records with conditions by page and
     * convert returned page to one with specified type.
     *
     * @param p        parameter box
     * @param pageInfo page information
     * @param qf       query function
     * @param cf       conversion function
     * @param <P>      parameter box type
     * @param <T>      entity type
     * @param <R>      target type
     * @return response
     */
    public <P, T extends BaseEntity, R> ResponseEntity findByPage(P p, Pageable pageInfo, BiFunction<P,
            Pageable, Page<T>> qf, Function<T, R> cf) {
        Page<T> origin = qf.apply(p, pageInfo);
        Page<R> target = this.from(origin, cf, pageInfo);
        return ResponseEntity.ok(target);
    }

    public <P, T extends BaseEntity, R> ResponseEntity findByList(P p, Function<P, List<T>> qf, Function<T, R> cf) {
        List<T> tList = qf.apply(p);
        List<R> result = Collections.emptyList();
        if (!CollectionUtils.isEmpty(tList)) {
            result = tList.stream().map(cf).collect(toList());
        }
        return ResponseEntity.ok(result);
    }

    public ResponseEntity getErrorResponse(BindingResult result) {
        String message = result.getFieldErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(joining("|"));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
    }

    private <D, T extends BaseEntity> ResponseEntity getCreatedResponse(Function<D, T> function, D d) {
        T t = function.apply(d);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(t.getId()).toUri();
        return ResponseEntity.created(location).build();
    }
}