from pydantic import BaseModel
from typing import Optional


class FarmaceuticoCreate(BaseModel):
    nome: str
    categoria: str
    quantidade: int
    valor: float
    tarja: str
    composicao: str
    retencao_de_receita: bool
    generico: bool
    receita: bool
    filial_id: int


class FarmaceuticoUpdate(BaseModel):
    nome: Optional[str] = None
    categoria: Optional[str] = None
    quantidade: Optional[int] = None
    valor: Optional[float] = None
    tarja: Optional[str] = None
    composicao: Optional[str] = None
    retencao_de_receita: Optional[bool] = None
    generico: Optional[bool] = None
    receita: Optional[bool] = None


class FarmaceuticoResponse(FarmaceuticoCreate):
    id: int
    nome: str
    categoria: str
    quantidade: int
    valor: float
    tarja: str
    composicao: str
    retencao_de_receita: bool
    generico: bool
    receita: bool
    filial_id: int

    class Config:
        from_attributes = True
