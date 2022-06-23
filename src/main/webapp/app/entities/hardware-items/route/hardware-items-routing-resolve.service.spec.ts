import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { IHardwareItems, HardwareItems } from '../hardware-items.model';
import { HardwareItemsService } from '../service/hardware-items.service';

import { HardwareItemsRoutingResolveService } from './hardware-items-routing-resolve.service';

describe('HardwareItems routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: HardwareItemsRoutingResolveService;
  let service: HardwareItemsService;
  let resultHardwareItems: IHardwareItems | undefined;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: {
            snapshot: {
              paramMap: convertToParamMap({}),
            },
          },
        },
      ],
    });
    mockRouter = TestBed.inject(Router);
    jest.spyOn(mockRouter, 'navigate').mockImplementation(() => Promise.resolve(true));
    mockActivatedRouteSnapshot = TestBed.inject(ActivatedRoute).snapshot;
    routingResolveService = TestBed.inject(HardwareItemsRoutingResolveService);
    service = TestBed.inject(HardwareItemsService);
    resultHardwareItems = undefined;
  });

  describe('resolve', () => {
    it('should return IHardwareItems returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultHardwareItems = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultHardwareItems).toEqual({ id: 123 });
    });

    it('should return new IHardwareItems if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultHardwareItems = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultHardwareItems).toEqual(new HardwareItems());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as HardwareItems })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultHardwareItems = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultHardwareItems).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
