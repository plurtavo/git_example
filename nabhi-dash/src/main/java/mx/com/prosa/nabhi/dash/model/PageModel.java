package mx.com.prosa.nabhi.dash.model;

import io.swagger.annotations.ApiModelProperty;

public class PageModel {

    @ApiModelProperty( value = "Paginación de la búsqueda", example = "1" )
    private int page;
    @ApiModelProperty( value = "Resultado por pagina de búsqueda, por default 10", example = "10" )
    private int results;

    public int getPage() {
        return page;
    }

    public void setPage( int page ) {
        this.page = page;
    }

    public int getResults() {
        return results;
    }

    public void setResults( int results ) {
        this.results = results;
    }
}
