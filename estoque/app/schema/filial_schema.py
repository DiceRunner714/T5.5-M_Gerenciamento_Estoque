from pydantic import BaseModel

class FilialCreate(BaseModel):
    nome: str
    local: str
    empresa_id: int

class FilialResponse(BaseModel):
    id: int
    nome: str
    local: str
    empresa_id: int

    class Config:
        from_attributes = True