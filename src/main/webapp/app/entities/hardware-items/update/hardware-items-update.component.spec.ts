import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { HardwareItemsService } from '../service/hardware-items.service';
import { IHardwareItems, HardwareItems } from '../hardware-items.model';
import { IRequirementItem } from 'app/entities/requirement-item/requirement-item.model';
import { RequirementItemService } from 'app/entities/requirement-item/service/requirement-item.service';

import { HardwareItemsUpdateComponent } from './hardware-items-update.component';

describe('HardwareItems Management Update Component', () => {
  let comp: HardwareItemsUpdateComponent;
  let fixture: ComponentFixture<HardwareItemsUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let hardwareItemsService: HardwareItemsService;
  let requirementItemService: RequirementItemService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [HardwareItemsUpdateComponent],
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
      .overrideTemplate(HardwareItemsUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(HardwareItemsUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    hardwareItemsService = TestBed.inject(HardwareItemsService);
    requirementItemService = TestBed.inject(RequirementItemService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call item query and add missing value', () => {
      const hardwareItems: IHardwareItems = { id: 456 };
      const item: IRequirementItem = { id: 49843 };
      hardwareItems.item = item;

      const itemCollection: IRequirementItem[] = [{ id: 57357 }];
      jest.spyOn(requirementItemService, 'query').mockReturnValue(of(new HttpResponse({ body: itemCollection })));
      const expectedCollection: IRequirementItem[] = [item, ...itemCollection];
      jest.spyOn(requirementItemService, 'addRequirementItemToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ hardwareItems });
      comp.ngOnInit();

      expect(requirementItemService.query).toHaveBeenCalled();
      expect(requirementItemService.addRequirementItemToCollectionIfMissing).toHaveBeenCalledWith(itemCollection, item);
      expect(comp.itemsCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const hardwareItems: IHardwareItems = { id: 456 };
      const item: IRequirementItem = { id: 9781 };
      hardwareItems.item = item;

      activatedRoute.data = of({ hardwareItems });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(hardwareItems));
      expect(comp.itemsCollection).toContain(item);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<HardwareItems>>();
      const hardwareItems = { id: 123 };
      jest.spyOn(hardwareItemsService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ hardwareItems });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: hardwareItems }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(hardwareItemsService.update).toHaveBeenCalledWith(hardwareItems);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<HardwareItems>>();
      const hardwareItems = new HardwareItems();
      jest.spyOn(hardwareItemsService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ hardwareItems });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: hardwareItems }));
      saveSubject.complete();

      // THEN
      expect(hardwareItemsService.create).toHaveBeenCalledWith(hardwareItems);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<HardwareItems>>();
      const hardwareItems = { id: 123 };
      jest.spyOn(hardwareItemsService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ hardwareItems });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(hardwareItemsService.update).toHaveBeenCalledWith(hardwareItems);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackRequirementItemById', () => {
      it('Should return tracked RequirementItem primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackRequirementItemById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
