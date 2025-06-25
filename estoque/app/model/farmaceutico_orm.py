from sqlalchemy import Column, Integer, String, Boolean, ForeignKey
from config.database import Base
from sqlalchemy.orm import relationship


class FarmaceuticoORM(Base):
    __tablename__ = "farmaceutico"

    id = Column(Integer, primary_key=True, index=True)
    nome = Column(String, nullable=False)
    crf = Column(String, nullable=False, unique=True)
    ativo = Column(Boolean, default=True)
    filial_id = Column(Integer, ForeignKey("filial.id", ondelete="CASCADE"))

    filial = relationship("FilialORM", back_populates="farmaceuticos")
