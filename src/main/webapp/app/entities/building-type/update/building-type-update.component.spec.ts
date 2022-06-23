import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { BuildingTypeService } from '../service/building-type.service';
import { IBuildingType, BuildingType } from '../building-type.model';

import { BuildingTypeUpdateComponent } from './building-type-update.component';

describe('BuildingType Management Update Component', () => {
  let comp: BuildingTypeUpdateComponent;
  let fixture: ComponentFixture<BuildingTypeUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let buildingTypeService: BuildingTypeService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [BuildingTypeUpdateComponent],
      providers: [
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(BuildingTypeUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(BuildingTypeUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    buildingTypeService = TestBed.inject(BuildingTypeService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const buildingType: IBuildingType = { id: 456 };

      activatedRoute.data = of({ buildingType });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(buildingType));
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<BuildingType>>();
      const buildingType = { id: 123 };
      jest.spyOn(buildingTypeService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ buildingType });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: buildingType }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(buildingTypeService.update).toHaveBeenCalledWith(buildingType);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<BuildingType>>();
      const buildingType = new BuildingType();
      jest.spyOn(buildingTypeService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ buildingType });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: buildingType }));
      saveSubject.complete();

      // THEN
      expect(buildingTypeService.create).toHaveBeenCalledWith(buildingType);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<BuildingType>>();
      const buildingType = { id: 123 };
      jest.spyOn(buildingTypeService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ buildingType });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(buildingTypeService.update).toHaveBeenCalledWith(buildingType);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
